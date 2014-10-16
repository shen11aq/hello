#include<iostream>
#include<stdio.h>
using namespace std;

class StreamBuffer
{
public:
    StreamBuffer();
    ~StreamBuffer();
    int ReceiveDate(unsigned int offset, unsigned int bytes, char *pData);
    int ContinueBytes(unsigned int &iDataOffset, char* &pData);
    int RemoveData(int iBytes);
private:
    char m_data[512*1024];//存数据的buffer，也可以用指针
    unsigned int offset;
    unsigned int len;
};
StreamBuffer::StreamBuffer()//构造函数
{
    for(int i=0; i<512*1024; i++)
    {
        m_data[i]='0';
    }
    offset=0;
    len=0;
}
StreamBuffer::~StreamBuffer()//析构函数
{

}
int StreamBuffer::ReceiveDate(unsigned int offset, unsigned int bytes, char *pData)
{
    int iBytes = 0;
    do
    {
        m_data[len+iBytes]=pData[iBytes];//把传过来的数据块读入m_data中，即复制到原有数据之后
        iBytes++;
    }
    while(iBytes<bytes);
    len=len+bytes;
    return iBytes;//返回传入了多大的数据块
}
int StreamBuffer::ContinueBytes(unsigned int &iDataOffset, char* &pData)
{
    int iContinueBytes = 0;
    iDataOffset=offset;
    pData=m_data;
    iContinueBytes=len;
    return iContinueBytes;
};
int StreamBuffer::RemoveData(int iBytes)
{
    int iBytesRemoved=0;
    do
    {
        m_data[iBytesRemoved]=m_data[iBytes+iBytesRemoved];
        iBytesRemoved++;
    }while(iBytesRemoved<len);
    len=len-iBytes;
    offset=offset-iBytes;
    return iBytesRemoved;
};
int main()
{
    char srcfileName[500]="E:\\baidumusic\\unnamed.mp3";
    char dstfileName[500]="E:\\baidumusic\\unnamed.mp3";
    FILE* fpSrcFile = NULL;
    FILE* fpDstFile = NULL;
    char Buf[1500*2];
    unsigned int iOffset;
    int iReadBytes = 0, iWantReadBytes;

    int  iContinueBytes;
    int iUseBytes;
    unsigned int iOutDataOffset;
    char  *pOutData;

    StreamBuffer MyBuffer;//定义一个类

    fpSrcFile = fopen(srcfileName, "rb");
    if( fpSrcFile == NULL )
    {
        cout<<"Cann't open file: "<<srcfileName<<endl;
        return 1;
    }

    fpDstFile = fopen(dstfileName, "wb");
    if( fpDstFile == NULL )
    {
        cout<<"Cann't create file: "<< dstfileName <<endl;
        return 2;
    }

    iOffset =  ftell(fpSrcFile);
    iWantReadBytes = 1024;
    iReadBytes = fread(Buf, 1, iWantReadBytes, fpSrcFile);
    while( iReadBytes > 0 )
    {
        MyBuffer.ReceiveDate(iOffset,iReadBytes,Buf);
        iContinueBytes = MyBuffer.ContinueBytes(iOutDataOffset, pOutData);
        if( iContinueBytes  > 1024) //示例数值，可以调整
        {
            iUseBytes =    iContinueBytes - 100;//假设用了一部分
            fseek(fpDstFile,iOutDataOffset,SEEK_SET);
            fwrite(pOutData,iUseBytes,1,fpDstFile);
            MyBuffer.RemoveData(iUseBytes);
        }
        iOffset =  ftell(fpSrcFile);
        iWantReadBytes =  iOffset%500+500;
        iReadBytes = fread(Buf, 1, iWantReadBytes, fpSrcFile);
    }
    //输入流结束，把缓冲区中所有排好序的数据取出
    iContinueBytes = MyBuffer.ContinueBytes(iOutDataOffset, pOutData);
    fseek(fpDstFile,iOutDataOffset,SEEK_SET);

    fwrite(pOutData,iContinueBytes,1,fpDstFile);
    fclose(fpDstFile);
    fclose(fpSrcFile);
    return 0;
}
